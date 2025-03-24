# MMIPlatform_Back

## 1. Introduction

L'Université de Meaux ne dispose actuellement d'aucune application permettant de gérer efficacement les notes, les justificatifs d'absence et la disponibilité des salles. En réponse à ce besoin, le projet **MMI Platform** a été initié par notre équipe d'étudiants de 3ème année en MMI.

Cette application a pour objectif de centraliser et de simplifier la gestion académique et administrative grâce à des outils modernes, tels que :
- Une vue interactive 3D des salles,
- Une gestion avancée des matrices Excel, permettant notamment la gestion des notes des étudiants.

## 2. Architecture

Le back-end de l'application est développé en Java Spring Boot et adopte une architecture RESTful. Afin d'assurer la scalabilité et la maintenabilité du code, chaque fonctionnalité est découpée en microservices Java respectant les principes de l'architecture hexagonale.

![Diagramme d'architecture technique](src%2Fmain%2Fresources%2FDocumentation%2Fdiagramme_darchitecture_technique.png)

Ce diagramme illustre l'organisation des différents microservices Java qui composent le back-end de l'application MMI Platform. Nous avons séparé :
- Les objets métiers Java (**Domain**),
- Les objets de données de la couche infrastructure (**DAO** - Data Access Object),
- Les objets exposés au client via l'application (**DTO** - Data Transfer Object).

Les trois couches distinctes sont :
1. **La couche métier (Domain)** : indépendante de toute dépendance technique,
2. **La couche infrastructure** : dédiée à la gestion des données,
3. **La couche application** : exposant les fonctionnalités à l'application cliente.

Le **Domain** ne possède aucune dépendance, tandis que les couches **Application** et **Infrastructure** dépendent de celle-ci. Chaque couche dispose de son propre fichier `pom.xml`, tout en étant centralisée par un fichier global.

Pour les tests, nous utilisons une base de données **H2** en mémoire. Cette approche permet de tester nos services sans déployer une base de données externe.

Un repository GitHub est utilisé comme référentiel via un adaptateur sortant dans la couche Infrastructure. Cela permet de récupérer la liste des étudiants ainsi que les ressources des matrices des 1ère, 2ème et 3ème années.

## 3. Reactive et Servlet Stack

Dans ce back-end, deux approches sont utilisées en Spring :
- Pour les fonctionnalités classiques de type **CRUD**, nous utilisons la **Servlet Stack** de Spring.
- Pour les fonctionnalités avancées nécessitant une gestion asynchrone, nous utilisons la **Reactive Stack** de Spring.

## 4. Architecture fonctionnelle de l'application

![Architecture fonctionnelle](src%2Fmain%2Fresources%2FDocumentation%2FArchitecture_fonctionnelle.png)

Ce diagramme illustre l'organisation des différents microservices Java qui composent le back-end de l'application MMI Platform. Chaque flux est représenté, ce qui permet de visualiser les différentes étapes de traitement des données côté client.

## 5. Sécurité

Pour sécuriser l'application, un système de token **JWT** (JSON Web Token) a été mis en place.
- Le token est généré lors de la connexion de l'utilisateur et stocké dans le **local storage** du navigateur.
- Ce token est ensuite envoyé dans le header de chaque requête HTTP pour authentifier l'utilisateur et vérifier ses autorisations.

## 6. Installation

Pour installer le projet, commencez par cloner le repository sur votre machine. Ensuite, installez les dépendances Maven en exécutant la commande suivante :

```bash
mvn clean install
```

Pour lancer le projet, exécutez :

```bash
mvn spring-boot:run
```

**Attention !** Vous devez disposer d'un JDK et de Maven installés sur votre machine. Si vous utilisez IntelliJ IDEA, vous pouvez également lancer le projet directement depuis l'IDE.

## 7. Documentation

### Diagramme de classes :
![Diagramme de classe](src%2Fmain%2Fresources%2FDocumentation%2FDiagrrammeDeClasse.png)

### Diagramme de séquences :
![Diagramme de séquence](src%2Fmain%2Fresources%2FDocumentation%2Fdiagramme_de_sequence.png)

Lorsque le back-end se lance, il permet l'exécution de requêtes HTTP pour récupérer les données de la base de données. Ces données sont ensuite envoyées au front-end pour être affichées à l'utilisateur. Cependant, avant toute utilisation, le back-end effectue des tâches planifiées. Il interroge notre référentiel GitHub qui se trouve dans un repository via l'API GitHub et sauvegarde dans la base de données la liste des étudiants par promotion, ainsi que la liste des modules de MMI allant de la première à la troisième année. 

### MCD :
![MCD](src%2Fmain%2Fresources%2FDocumentation%2Fmcdv2.png)

### Contraintes techniques d'implémentation

Pour plus de détails, veuillez consulter le document suivant :
[Contraintes techniques d'implémentation (Back-End).pdf](src%2Fmain%2Fresources%2FDocumentation%2FContraintes%20techniques%20d%27imple%CC%81mentation%20%28Back-End%29.pdf)

## 8. Accès

- **Accès à l'API** : [http://localhost:8080/mmiplatform/](http://localhost:8080/mmiplatform/)
- **Accès à Swagger** : [http://localhost:8080/mmiplatform/swagger-ui.html](http://localhost:8080/mmiplatform/swagger-ui.html)
