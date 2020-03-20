# MVC React Portlet

A PoC MVC React portlet built for Liferay

## Showcases

Passing data trough render-request 

Renders our React App using`<react:component>` tag

```
<react:component
	data="<%= data %>"
	module="js/Index.es"
/>
```
Using ClayUI for the UI components

## How to Build and Deploy to Liferay
Build it in a Liferay Workspace or provide a gradle wrapper for the project
### Build it
` $ ../../gradlew build `

### Deploy to Liferay
` $ ../../gradlew deploy `


## Plans for future Improvements (can change):

### Use different UI library

### Use Managementbar with custom data

### Use Rest Api with Liferay
