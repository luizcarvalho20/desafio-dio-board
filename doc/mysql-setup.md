# Setup MySQL (Desafio Board)

## Criar banco e usuário

> O banco possui hífen no nome, então precisa de crase no MySQL.

```sql
CREATE DATABASE IF NOT EXISTS `Desafio-Dio-MySql` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'LuizCarvalho'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON `Desafio-Dio-MySql`.* TO 'LuizCarvalho'@'localhost';
FLUSH PRIVILEGES;
