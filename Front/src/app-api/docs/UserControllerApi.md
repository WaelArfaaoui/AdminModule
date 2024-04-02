# OpenapiClient::UserControllerApi

All URIs are relative to *http://localhost:8090*

| Method | HTTP request | Description |
| ------ | ------------ | ----------- |
| [**add**](UserControllerApi.md#add) | **POST** /api/users/add |  |
| [**change_password**](UserControllerApi.md#change_password) | **PATCH** /api/users/changepassword |  |
| [**delete**](UserControllerApi.md#delete) | **DELETE** /api/users/{id} |  |
| [**get_user**](UserControllerApi.md#get_user) | **GET** /api/users/get/{email} |  |
| [**getusers**](UserControllerApi.md#getusers) | **GET** /api/users |  |
| [**update**](UserControllerApi.md#update) | **PUT** /api/users/{id} |  |


## add

> <UserDto> add(user_dto)



### Examples

```ruby
require 'time'
require 'openapi_client'
# setup authorization
OpenapiClient.configure do |config|
  # Configure Bearer authorization (JWT): bearerAuth
  config.access_token = 'YOUR_BEARER_TOKEN'
end

api_instance = OpenapiClient::UserControllerApi.new
user_dto = OpenapiClient::UserDto.new # UserDto | 

begin
  
  result = api_instance.add(user_dto)
  p result
rescue OpenapiClient::ApiError => e
  puts "Error when calling UserControllerApi->add: #{e}"
end
```

#### Using the add_with_http_info variant

This returns an Array which contains the response data, status code and headers.

> <Array(<UserDto>, Integer, Hash)> add_with_http_info(user_dto)

```ruby
begin
  
  data, status_code, headers = api_instance.add_with_http_info(user_dto)
  p status_code # => 2xx
  p headers # => { ... }
  p data # => <UserDto>
rescue OpenapiClient::ApiError => e
  puts "Error when calling UserControllerApi->add_with_http_info: #{e}"
end
```

### Parameters

| Name | Type | Description | Notes |
| ---- | ---- | ----------- | ----- |
| **user_dto** | [**UserDto**](UserDto.md) |  |  |

### Return type

[**UserDto**](UserDto.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


## change_password

> <ChangePassword> change_password(change_password)



### Examples

```ruby
require 'time'
require 'openapi_client'
# setup authorization
OpenapiClient.configure do |config|
  # Configure Bearer authorization (JWT): bearerAuth
  config.access_token = 'YOUR_BEARER_TOKEN'
end

api_instance = OpenapiClient::UserControllerApi.new
change_password = OpenapiClient::ChangePassword.new # ChangePassword | 

begin
  
  result = api_instance.change_password(change_password)
  p result
rescue OpenapiClient::ApiError => e
  puts "Error when calling UserControllerApi->change_password: #{e}"
end
```

#### Using the change_password_with_http_info variant

This returns an Array which contains the response data, status code and headers.

> <Array(<ChangePassword>, Integer, Hash)> change_password_with_http_info(change_password)

```ruby
begin
  
  data, status_code, headers = api_instance.change_password_with_http_info(change_password)
  p status_code # => 2xx
  p headers # => { ... }
  p data # => <ChangePassword>
rescue OpenapiClient::ApiError => e
  puts "Error when calling UserControllerApi->change_password_with_http_info: #{e}"
end
```

### Parameters

| Name | Type | Description | Notes |
| ---- | ---- | ----------- | ----- |
| **change_password** | [**ChangePassword**](ChangePassword.md) |  |  |

### Return type

[**ChangePassword**](ChangePassword.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


## delete

> delete(id)



### Examples

```ruby
require 'time'
require 'openapi_client'
# setup authorization
OpenapiClient.configure do |config|
  # Configure Bearer authorization (JWT): bearerAuth
  config.access_token = 'YOUR_BEARER_TOKEN'
end

api_instance = OpenapiClient::UserControllerApi.new
id = 56 # Integer | 

begin
  
  api_instance.delete(id)
rescue OpenapiClient::ApiError => e
  puts "Error when calling UserControllerApi->delete: #{e}"
end
```

#### Using the delete_with_http_info variant

This returns an Array which contains the response data (`nil` in this case), status code and headers.

> <Array(nil, Integer, Hash)> delete_with_http_info(id)

```ruby
begin
  
  data, status_code, headers = api_instance.delete_with_http_info(id)
  p status_code # => 2xx
  p headers # => { ... }
  p data # => nil
rescue OpenapiClient::ApiError => e
  puts "Error when calling UserControllerApi->delete_with_http_info: #{e}"
end
```

### Parameters

| Name | Type | Description | Notes |
| ---- | ---- | ----------- | ----- |
| **id** | **Integer** |  |  |

### Return type

nil (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined


## get_user

> <User> get_user(email)



### Examples

```ruby
require 'time'
require 'openapi_client'
# setup authorization
OpenapiClient.configure do |config|
  # Configure Bearer authorization (JWT): bearerAuth
  config.access_token = 'YOUR_BEARER_TOKEN'
end

api_instance = OpenapiClient::UserControllerApi.new
email = 'email_example' # String | 

begin
  
  result = api_instance.get_user(email)
  p result
rescue OpenapiClient::ApiError => e
  puts "Error when calling UserControllerApi->get_user: #{e}"
end
```

#### Using the get_user_with_http_info variant

This returns an Array which contains the response data, status code and headers.

> <Array(<User>, Integer, Hash)> get_user_with_http_info(email)

```ruby
begin
  
  data, status_code, headers = api_instance.get_user_with_http_info(email)
  p status_code # => 2xx
  p headers # => { ... }
  p data # => <User>
rescue OpenapiClient::ApiError => e
  puts "Error when calling UserControllerApi->get_user_with_http_info: #{e}"
end
```

### Parameters

| Name | Type | Description | Notes |
| ---- | ---- | ----------- | ----- |
| **email** | **String** |  |  |

### Return type

[**User**](User.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## getusers

> <Array<UserDto>> getusers



### Examples

```ruby
require 'time'
require 'openapi_client'
# setup authorization
OpenapiClient.configure do |config|
  # Configure Bearer authorization (JWT): bearerAuth
  config.access_token = 'YOUR_BEARER_TOKEN'
end

api_instance = OpenapiClient::UserControllerApi.new

begin
  
  result = api_instance.getusers
  p result
rescue OpenapiClient::ApiError => e
  puts "Error when calling UserControllerApi->getusers: #{e}"
end
```

#### Using the getusers_with_http_info variant

This returns an Array which contains the response data, status code and headers.

> <Array(<Array<UserDto>>, Integer, Hash)> getusers_with_http_info

```ruby
begin
  
  data, status_code, headers = api_instance.getusers_with_http_info
  p status_code # => 2xx
  p headers # => { ... }
  p data # => <Array<UserDto>>
rescue OpenapiClient::ApiError => e
  puts "Error when calling UserControllerApi->getusers_with_http_info: #{e}"
end
```

### Parameters

This endpoint does not need any parameter.

### Return type

[**Array&lt;UserDto&gt;**](UserDto.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## update

> <UserDto> update(id, opts)



### Examples

```ruby
require 'time'
require 'openapi_client'
# setup authorization
OpenapiClient.configure do |config|
  # Configure Bearer authorization (JWT): bearerAuth
  config.access_token = 'YOUR_BEARER_TOKEN'
end

api_instance = OpenapiClient::UserControllerApi.new
id = 56 # Integer | 
opts = {
  firstname: 'firstname_example', # String | 
  lastname: 'lastname_example', # String | 
  email: 'email_example', # String | 
  password: 'password_example', # String | 
  phone: 'phone_example', # String | 
  role: 'BUSINESSEXPERT', # String | 
  update_request: OpenapiClient::UpdateRequest.new # UpdateRequest | 
}

begin
  
  result = api_instance.update(id, opts)
  p result
rescue OpenapiClient::ApiError => e
  puts "Error when calling UserControllerApi->update: #{e}"
end
```

#### Using the update_with_http_info variant

This returns an Array which contains the response data, status code and headers.

> <Array(<UserDto>, Integer, Hash)> update_with_http_info(id, opts)

```ruby
begin
  
  data, status_code, headers = api_instance.update_with_http_info(id, opts)
  p status_code # => 2xx
  p headers # => { ... }
  p data # => <UserDto>
rescue OpenapiClient::ApiError => e
  puts "Error when calling UserControllerApi->update_with_http_info: #{e}"
end
```

### Parameters

| Name | Type | Description | Notes |
| ---- | ---- | ----------- | ----- |
| **id** | **Integer** |  |  |
| **firstname** | **String** |  | [optional] |
| **lastname** | **String** |  | [optional] |
| **email** | **String** |  | [optional] |
| **password** | **String** |  | [optional] |
| **phone** | **String** |  | [optional] |
| **role** | **String** |  | [optional] |
| **update_request** | [**UpdateRequest**](UpdateRequest.md) |  | [optional] |

### Return type

[**UserDto**](UserDto.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

