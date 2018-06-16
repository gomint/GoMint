#include <stdlib.h>
#include <zlib.h>
#include "io_gomint_server_jni_zlib_ZLibNative.h"

typedef unsigned char byte;

static jfieldID consumedID;
static jfieldID finishedID;

static jclass exceptionClass;
static jmethodID exceptionConstructor;

jint throwException(JNIEnv *env, const char* message, int err) {
	jstring jMessage = env->NewStringUTF(message);

	jthrowable throwable = (jthrowable)env->NewObject(exceptionClass, exceptionConstructor, jMessage, err);
	return env->Throw(throwable);
}

// Is automatically called once the native code is loaded via System.loadLibary(...);
jint JNI_OnLoad(JavaVM* vm, void* reserved) {
	JNIEnv* env;
	if (vm->GetEnv((void **)&env, JNI_VERSION_1_6) != JNI_OK) {
		return JNI_ERR;
	} else {
		jclass localExceptionClass = env->FindClass("io/gomint/server/jni/exception/NativeException");
		exceptionClass = (jclass) env->NewGlobalRef(localExceptionClass);
		exceptionConstructor = env->GetMethodID(exceptionClass, "<init>", "(Ljava/lang/String;I)V");
	}

	return JNI_VERSION_1_6;
}

// Is automatically called once the Classloader is destroyed
void JNI_OnUnload(JavaVM *vm, void *reserved) {
	JNIEnv* env;
	if (vm->GetEnv((void **)&env, JNI_VERSION_1_6) != JNI_OK) {
		// Something is wrong but nothing we can do about this :(
		return;
	} else {
		// delete global references so the GC can collect them
		if (exceptionClass != NULL) {
			env->DeleteGlobalRef(exceptionClass);
		}
	}
}

void JNICALL Java_io_gomint_server_jni_zlib_ZLibNative_initFields(JNIEnv* env, jclass clazz) {
	// We trust that these fields will be there
	consumedID = env->GetFieldID(clazz, "consumed", "I");
	finishedID = env->GetFieldID(clazz, "finished", "Z");
}

void JNICALL Java_io_gomint_server_jni_zlib_ZLibNative_reset(JNIEnv* env, jobject obj, jlong ctx, jboolean compress) {
	z_stream* stream = (z_stream*)ctx;
	int ret = (compress) ? deflateReset(stream) : inflateReset(stream);

	if (ret != Z_OK) {
		throwException(env, "Could not reset z_stream", ret);
	}
}

void JNICALL Java_io_gomint_server_jni_zlib_ZLibNative_end(JNIEnv* env, jobject obj, jlong ctx, jboolean compress) {
	z_stream* stream = (z_stream*)ctx;
	int ret = (compress) ? deflateEnd(stream) : inflateEnd(stream);

	free(stream);

	if (ret != Z_OK) {
		throwException(env, "Could not free z_stream: ", ret);
	}
}

jlong JNICALL Java_io_gomint_server_jni_zlib_ZLibNative_init(JNIEnv* env, jobject obj, jboolean compress, jint level) {
	z_stream* stream = (z_stream*)calloc(1, sizeof(z_stream));
	int ret = (compress) ? deflateInit(stream, level) : inflateInit(stream);

	if (ret != Z_OK) {
		throwException(env, "Could not init z_stream", ret);
	}

	return (jlong)stream;
}

jint JNICALL Java_io_gomint_server_jni_zlib_ZLibNative_process(JNIEnv* env, jobject obj, jlong ctx, jlong in, jint inLength, jlong out, jint outLength, jboolean compress) {
	z_stream* stream = (z_stream*)ctx;

	stream->avail_in = inLength;
	stream->next_in = (byte*)in;

	stream->avail_out = outLength;
	stream->next_out = (byte*)out;

	int ret = (compress) ? deflate(stream, Z_FINISH) : inflate(stream, Z_PARTIAL_FLUSH);

	switch (ret) {
	case Z_STREAM_END:
		env->SetBooleanField(obj, finishedID, true);
		break;
	case Z_OK:
		break;
	default:
	    throwException(env, "Unknown z_stream return code", ret);
	}

	env->SetIntField(obj, consumedID, inLength - stream->avail_in);

	return outLength - stream->avail_out;
}
