pipeline {
  agent {
    docker {
      image 'maven:alpine'
      args '-v /root/.m2:/root/.m2 -u root'
    }
  }
  stages {
    stage('Depends') {
      steps {
        sh 'apk --no-cache add ca-certificates wget'
        sh 'wget --quiet --output-document=/etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub'
        sh 'wget https://github.com/sgerrand/alpine-pkg-java-openjfx/releases/download/8.151.12-r0/java-openjfx-8.151.12-r0.apk'
        sh 'apk add --no-cache java-openjfx-8.151.12-r0.apk'
      }
    }
    stage('Build') {
      steps {
        sh 'mvn -U -B -DskipTests clean install'
      }
    }
    stage('Store') {
      steps {
        archiveArtifacts 'gomint-server/target/GoMint.jar'
      }
    }
  }
  post {
    success {
      withCredentials([string(credentialsId: 'discord', variable: 'webhookUrl')]) {
        discordSend title: "#${currentBuild.id} ${JOB_NAME}", link: currentBuild.absoluteUrl, footer: "Provided with <3", successful: currentBuild.resultIsBetterOrEqualTo('SUCCESS'), webhookURL: "${webhookUrl}", description: "GoMint Build succeeded.\n${currentBuild.absoluteUrl}artifact/gomint-server/target/GoMint.jar"
      }
    }
    failure {
      withCredentials([string(credentialsId: 'discord', variable: 'webhookUrl')]) {
        discordSend title: "#${currentBuild.id} ${JOB_NAME}", link: currentBuild.absoluteUrl, footer: "Provided with <3", successful: currentBuild.resultIsBetterOrEqualTo('SUCCESS'), webhookURL: "${webhookUrl}", description: "GoMint Build failed."
      }
    }
  }
}