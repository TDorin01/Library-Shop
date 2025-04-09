# Folosește o imagine Java slim pentru runtime
FROM eclipse-temurin:17-jdk-alpine

# Setează un director de lucru în container
WORKDIR /app

# Copiază jar-ul compilat în container
COPY build/libs/*.jar app.jar

# Expune portul pe care rulează aplicația (modifică dacă ai altul)
EXPOSE 8080

# Comanda de start
ENTRYPOINT ["java", "-jar", "app.jar"]
