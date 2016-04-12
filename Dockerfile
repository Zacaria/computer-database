FROM jenkins

USER root

RUN curl -sSLk https://get.docker.com/ | sh && rm -rf /var/lib/apt/lists/*

ENV DOCKER_HOST=tcp://dind_cdb:2375

RUN service docker start

USER jenkins

RUN usermod -aG docker $(whoami)

WORKDIR 

COPY docker-compose-dind.yml /home