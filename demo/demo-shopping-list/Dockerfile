FROM adoptopenjdk/openjdk13:jdk-13.0.2_8-alpine

RUN  addgroup -S sboot && adduser -S -G sboot sboot  \
    && mkdir /opt/service \
    && chown sboot:sboot /opt/service

USER sboot
WORKDIR /opt/service

ADD build/exploded/BOOT-INF/lib BOOT-INF/lib
ADD build/exploded/org org
ADD build/exploded/META-INF META-INF
ADD build/exploded/BOOT-INF/classes BOOT-INF/classes

ENTRYPOINT java -cp . org.springframework.boot.loader.JarLauncher
