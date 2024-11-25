# Use Node.js as the base image for building
FROM node:16 as build
WORKDIR /app

# Copy the package.json and package-lock.json files
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code to the working directory
COPY . .

# Build the React app
ARG BUILD_MODE=production
RUN npm run build:${BUILD_MODE}

# Use a lightweight NGINX base image for serving the built app
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

# Expose port 3000 for the web server
EXPOSE 3000

# Run NGINX
CMD ["nginx", "-g", "daemon off;"]
