#!/bin/bash

KUBECONFIG=$("$KUBE_CONFIG" | base64 --decode) kubectl apply -f deployment.yaml
KUBECONFIG=$("$KUBE_CONFIG" | base64 --decode) kubectl rollout restart deployment/goods
