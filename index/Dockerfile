FROM node:12 AS build
# install node packages
WORKDIR /opt/index
ADD ./* .
RUN npm set progress=false && npm config set depth 0
RUN npm install
RUN npm run build

FROM node:12
WORKDIR /opt/index
COPY --from=build /opt/index/node_modules ./node_modules
COPY --from=build /opt/index/dist ./dist
EXPOSE 18082
CMD npm run start
