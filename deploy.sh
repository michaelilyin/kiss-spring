#!/bin/bash

mkdir ${HOME}/.kube
echo "$KCONF" | base64 --decode > ${HOME}/.kube/config

kubectl apply -f deployment.yaml
kubectl rollout restart --namespace kiss-cloud deployment/goods
kubectl rollout restart --namespace kiss-cloud deployment/demo-shopping-list

kubectl apply -f deployment-hrh.yaml
kubectl rollout restart --namespace hrh-cloud deployment/items
kubectl rollout restart --namespace hrh-cloud deployment/houses
