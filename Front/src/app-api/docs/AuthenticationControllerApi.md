# OpenapiClient::AuthenticationControllerApi

All URIs are relative to *http://localhost:8090*

| Method | HTTP request | Description |
| ------ | ------------ | ----------- |
| [**authenticate**](AuthenticationControllerApi.md#authenticate) | **POST** /api/auth/authenticate |  |
| [**refresh_token**](AuthenticationControllerApi.md#refresh_token) | **POST** /api/auth/refresh-token |  |


## authenticate

> <AuthenticationResponse> authenticate(authentication_request)



### Examples

```ruby
require 'time'
require 'openapi_client'
# setup authorization
OpenapiClient.configure do |config|
  # Configure Bearer authorization (JWT): bearerAuth
  config.access_token = 'YOUR_BEARER_TOKEN'
end

api_instance = OpenapiClient::AuthenticationControllerApi.new
authentication_request = OpenapiClient::AuthenticationRequest.new # AuthenticationRequest | 

begin
  
  result = api_instance.authenticate(authentication_request)
  p result
rescue OpenapiClient::ApiError => e
  puts "Error when calling AuthenticationControllerApi->authenticate: #{e}"
end
```

#### Using the authenticate_with_http_info variant

This returns an Array which contains the response data, status code and headers.

> <Array(<AuthenticationResponse>, Integer, Hash)> authenticate_with_http_info(authentication_request)

```ruby
begin
  
  data, status_code, headers = api_instance.authenticate_with_http_info(authentication_request)
  p status_code # => 2xx
  p headers # => { ... }
  p data # => <AuthenticationResponse>
rescue OpenapiClient::ApiError => e
  puts "Error when calling AuthenticationControllerApi->authenticate_with_http_info: #{e}"
end
```

### Parameters

| Name | Type | Description | Notes |
| ---- | ---- | ----------- | ----- |
| **authentication_request** | [**AuthenticationRequest**](AuthenticationRequest.md) |  |  |

### Return type

[**AuthenticationResponse**](AuthenticationResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


## refresh_token

> refresh_token



### Examples

```ruby
require 'time'
require 'openapi_client'
# setup authorization
OpenapiClient.configure do |config|
  # Configure Bearer authorization (JWT): bearerAuth
  config.access_token = 'YOUR_BEARER_TOKEN'
end

api_instance = OpenapiClient::AuthenticationControllerApi.new

begin
  
  api_instance.refresh_token
rescue OpenapiClient::ApiError => e
  puts "Error when calling AuthenticationControllerApi->refresh_token: #{e}"
end
```

#### Using the refresh_token_with_http_info variant

This returns an Array which contains the response data (`nil` in this case), status code and headers.

> <Array(nil, Integer, Hash)> refresh_token_with_http_info

```ruby
begin
  
  data, status_code, headers = api_instance.refresh_token_with_http_info
  p status_code # => 2xx
  p headers # => { ... }
  p data # => nil
rescue OpenapiClient::ApiError => e
  puts "Error when calling AuthenticationControllerApi->refresh_token_with_http_info: #{e}"
end
```

### Parameters

This endpoint does not need any parameter.

### Return type

nil (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

