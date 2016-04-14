FROM jenkins

USER root

RUN curl -sSLk https://get.docker.com/ | sh && rm -rf /var/lib/apt/lists/*

RUN curl -L https://github.com/docker/compose/releases/download/1.6.2/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
RUN chmod +x /usr/local/bin/docker-compose

ENV DOCKER_HOST tcp://dind-cdb:2375

RUN service docker start

USER jenkins

RUN usermod -aG docker $(whoami)

#COPY docker-compose-dind.yml /home