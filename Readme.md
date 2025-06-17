MDD API - Spring Boot REST API

Cette API REST permet de gérer une plateforme de publication d'articles avec des thèmes, des utilisateurs, des commentaires, et des abonnements.

## **Technologies utilisées**

Java 17+

Spring Boot 2.7+

Spring Security

JWT (JSON Web Tokens)

Hibernate / JPA

MySQL 

## **Fonctionnalités**

_**Authentification**_

POST /api/auth/register : inscription

POST /api/auth/login : connexion (retourne un JWT)

**_Utilisateur_**

GET /api/user/me : infos du profil connecté

PUT /api/user/update/me : modifier profil (username, email, mot de passe)

_**Thèmes**_

POST /api/themes : créer un thème (admin uniquement)

GET /api/themes : lister tous les thèmes

GET /api/themes/{id} : détails d'un thème

**_Articles_**

POST /api/articles : créer un article (authentifié)

GET /api/articles : lister les articles

GET /api/articles/{id} : détails d'un article

**_Commentaires_**

POST /api/comments : ajouter un commentaire à un article

GET /api/comments/article/{id} : commentaires d'un article

DELETE /api/comments/{id} : supprimer un commentaire (auteur uniquement)

**_Abonnements_**

POST /api/subscriptions : s'abonner à un thème

DELETE /api/subscriptions/{id} : se désabonner

# **Exemples d'utilisation avec Postman**

## **_Authentification_**

`POST /api/auth/login
{
"email": "user@example.com",
"password": "Password123!"
}`

## **Création de thème (admin uniquement)**

`POST /api/themes
Headers: Authorization: Bearer <token>
Body:
{
"name": "Programmation",
"description": "Actualités et tutoriels sur le dev"
}`

## **_Création d'article_**

`POST /api/articles
Headers: Authorization: Bearer <token>
Body:
{
"title": "Introduction à Spring Boot",
"content": "Spring Boot est un framework...",
"themeId": 1
}`

## **_Ajouter un commentaire_**

`POST /api/comments
Headers: Authorization: Bearer <token>
Body:
{
"content": "Merci pour cet article !",
"articleId": 2
}`

_**Configuration JWT**_

`jwt.secret=secretKey123456789
jwt.expiration=86400000`

_**Lancement de l'application**_

`mvn spring-boot:run`