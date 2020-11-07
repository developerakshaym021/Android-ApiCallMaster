# API-Call-Master

Add it in your root build.gradle at the end of repositories: "# ApiCall"
1. repository

  
  
                   allprojects 
                 {
       
		     repositories {
    
			maven { 
			url 'https://jitpack.io'
			}
		}
	
             } 
  
  Step 2. Add the dependency
  
  
  	dependencies {
	        implementation 'com.github.developerakshaym021:Android-ApiCallMaster:v1.1.3'
	}
  
      private Method method=Method.POST;

  
"# ApiCall"


Step 3. How use "# ApiCall"


               ApiCallBuilder.build(this)
                .isShowProgressBar(true)
                .setMethod(method)
                .setUrl("https://jsonplaceholder.typicode.com/todos/1")
                .setParam(getParam())//https://uniquehometutors.com/tutorapp/api/get_student_banners
                .setConnectionTimout(TimoutInSeconds)
                .execute(new ApiCallBuilder.onResponse() {
                    @Override
                    public void Success(String response) {
                        try {

                          
                        }catch (Exception e){
                            
                        }
                    }

                    @Override
                    public void Failed(String error) {
                        
                    }
                });
		
	private HashMap<String, String> getParam() {
          HashMap<String,String>param=new HashMap<>();
          return param;
    }
    
    
    



[![](https://jitpack.io/v/developerakshaym021/Android-ApiCallMaster.svg)](https://jitpack.io/#developerakshaym021/Android-ApiCallMaster)
