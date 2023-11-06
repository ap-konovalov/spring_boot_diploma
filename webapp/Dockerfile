FROM node:erbium-alpine3.12
WORKDIR /opt/app
COPY . .
RUN npm install
CMD ["npm", "run", "start"]
EXPOSE 3000