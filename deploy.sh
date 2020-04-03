#!/bin/bash

mkdir ${HOME}/.kube
echo "KCONF" | base64 --decode > ${HOME}/.kube/config

kubectl apply -f deployment.yaml
kubectl rollout restart deployment/goods
