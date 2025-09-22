FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .

RUN npm run build -- --configuration production

# Stage 2: Serve the application from Nginx
FROM nginx:alpine
# Copy the built output from the 'build' stage
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html
# Copy a custom Nginx configuration to handle SPA routing
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
