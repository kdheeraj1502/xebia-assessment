# xebia-assessment
An application to store articles on technologies 

#H2 Databse
H2 in memoery database is used

# Project Title

Article served along with description goes here

## Getting Started

These instructions will serve you article details saved in machine for testing purposes.

### Prerequisites

jdk 8 needs to be install in the system from oracle

```
jdk1.8
sts tool or initializer or CDI
```
## Entry point to save and fetch object

### To save Article
```
http://localhost:8085/api/articles/saveArticles
```
### To update Article with id
```
http://localhost:8085/api/articles/updateArticle/1
## 1 is articleId
```
### To fetch all the Article present on machine
```
http://localhost:8085/api/articles/allArticles?pageNo=0&pageSize=1
```
### To delete an Article using uuid present on machine
```
http://localhost:8085/api/articles/delete/a86e5be393c011eaabe3a95c7c9a6bf9
## uuid = a86e5be393c011eaabe3a95c7c9a6bf9
```
### To fetch all the Tags present on machine
```
http://localhost:8085/api/articles/allTags
```
### To fetch read time of the title of an Article using uuid
```
http://localhost:8085/api/articles/readtime/5a565d2293be11eab1a1e15e38bfd222
## uuid = a86e5be393c011eaabe3a95c7c9a6bf9
```
### To fetch count of the tags present on machine
```
http://localhost:8085/api/articles/tags/count?pageNo=0&pageSize=10
```
###Sample request
```
request body to save details in machine
{
	"title":"How to learn Spring Booot",
	"description":"Ever wonder how?",
	"body":"its gurgaon",
	"tags" :["spring", "java","hibernate"]
}
```
## Running the tests


test1

```

```

## Deployment



## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

 

## Authors

* **Dheeraj Kumar** 

## License

its for tutorial purpose 

## Acknowledgments

* my past experience of Sapient helped me to develop this application
