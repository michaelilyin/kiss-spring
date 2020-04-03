#!/bin/bash

KUBECONFIG=$(echo "$KUBE_CONFIG" | base64 --decode) kubectl apply -f deployment.yaml
KUBECONFIG=$(echo "$KUBE_CONFIG" | base64 --decode) kubectl rollout restart deployment/goods
