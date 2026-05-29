# Paso 1: Comienza con una imagen base que tenga Node.js instalado
FROM node:18-alpine

# Paso 2: Crea un directorio de trabajo dentro del container
WORKDIR /app

# Paso 3: Copia el archivo package.json
COPY package.json .

# Paso 4: Instala las dependencias
RUN npm install

# Paso 5: Copia el resto del código
COPY app.js .

# Paso 6: Documenta qué puerto usará (solo información)
EXPOSE 3000

# Paso 7: Comando para ejecutar cuando se inicie el container
CMD ["npm", "start"]

