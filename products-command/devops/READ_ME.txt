=== PARA QUÉ SIRVE CADA ARCHIVO (bien simple) ===

1. persistence-volume.yaml
   Es el DISCO DURO del cluster. 5GB de espacio en /mnt/data/.
   Los datos sobreviven aunque los contenedores se borren o reinicien.

2. persistence-volume-claim.yaml (PVC)
   Es la RESERVA del disco. El pod de PostgreSQL pide 2GB del disco (PV) de arriba.

3. configmap-postgres-initbd.yaml
   Es el guión de INICIO. Cuando PostgreSQL arranque, ejecuta este script
   para crear un usuario "billingapp" con contraseña "qwerty" y su base de datos.

4. secret-dev.yaml
   Guarda las CONTRASEÑAS de PostgreSQL (usuario, password, db).
   Están en base64 (no es seguro real, solo para desarrollo local).

5. secret-pgadmin.yaml
   Guarda las CONTRASEÑAS de pgAdmin (email, password, puerto).
   También en base64, solo para desarrollo local.