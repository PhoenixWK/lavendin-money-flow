@echo off
docker run --rm -v %cd%:/app -w /app maven:3.9.6-eclipse-temurin-21 mvn %*

