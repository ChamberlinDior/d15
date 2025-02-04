# Step 1: Build the application using Eclipse Temurin JDK 21 on Alpine and Maven Wrapper
FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /opt/app

# Configure UTF-8 pour éviter tout problème d'encodage
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

# Copier le Maven Wrapper et le fichier pom.xml
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

# Donner les droits d'exécution au wrapper et télécharger les dépendances hors ligne
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copier le code source et compiler l'application (en désactivant les tests pour accélérer)
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Step 2: Créer l'image d'exécution en utilisant une image JRE minimale
FROM eclipse-temurin:21-jre-alpine
WORKDIR /opt/app

# Copier le JAR généré à partir de l'étape de build
COPY --from=build /opt/app/target/*.jar app.jar

# Exposer le port 8080 (celui défini dans application.properties)
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
