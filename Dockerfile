FROM adoptopenjdk/openjdk11:alpine

# Set env first to reduce layers in the container image
ENV ALPINE_MIRROR="http://dl-cdn.alpinelinux.org/alpine"

# This is the line from AdoptOpenJDK:
RUN apk --update add --no-cache ca-certificates curl bash
COPY release/ /gomint/
RUN ls -al /gomint/
VOLUME /gomint/plugins
WORKDIR /gomint

CMD ["./start.sh"]
