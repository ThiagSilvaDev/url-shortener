FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .

RUN npm run build -- --configuration production

# Stage 2: Nginx to serve built app
FROM nginx:alpine
# Copy Angular build output (if project folder exists inside dist, adjust accordingly)
COPY --from=build /app/dist/*/browser /usr/share/nginx/html
# Copy custom nginx config (adjust path if different)
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
