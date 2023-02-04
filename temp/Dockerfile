FROM centos

RUN cd /etc/yum.repos.d/

RUN sed -i 's/mirrorlist/#mirrorlist/g' /etc/yum.repos.d/CentOS-*

RUN sed -i 's|#baseurl=http://mirror.centos.org|baseurl=http://vault.centos.org|g' /etc/yum.repos.d/CentOS-*

RUN yum update -y

RUN yum install -y java-17-openjdk

VOLUME /tmp

ADD /recipeapp-0.0.1-SNAPSHOT.jar myapp.jar

RUN sh -c 'touch ./myapp.jar'

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/myapp.jar"]