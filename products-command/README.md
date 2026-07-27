# Commands

## Kafka

### Levantar Kafka

```bash
docker run -d \
  --name kafka \
  -p 9092:9092 \
  --pull=always \
  apache/kafka:latest
```

### Crear el tópico `products.commands`

```bash
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --create \
  --topic products.commands \
  --partitions 1 \
  --replication-factor 1
```

### Crear el tópico `products.replies`

```bash
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --create \
  --topic products.replies \
  --partitions 1 \
  --replication-factor 1
```

### Listar tópicos

```bash
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --list
```

---

# Kubernetes Example

## Instalar herramientas

```bash
brew install minikube
brew install kubectl
```

## Iniciar Minikube

```bash
minikube start --memory=2048 --cpus=2
```

## Crear el Pod

```bash
kubectl run kbillingapp \
  --image=sotobotero/udemy-devops:0.0.1 \
  --port=80
```

## Obtener la URL del servicio

```bash
minikube service --url kbillingapp
```

## Ver detalles del Pod

```bash
kubectl describe pod kbillingapp
```

## Crear objetos en el clúster

```bash
kubectl apply -f devops/
```

<img width="1532" height="913" alt="Captura de pantalla 2026-07-26 a la(s) 8 16 44 p m" src="https://github.com/user-attachments/assets/f14166cc-14a1-4d6f-bdab-944e283314c8" />

## Eliminar objetos del clúster

```bash
kubectl delete -f devops/
```

## Comprobar estado de minikube

```bash
minikube status
```
