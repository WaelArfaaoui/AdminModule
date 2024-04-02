# OpenapiClient::User

## Properties

| Name | Type | Description | Notes |
| ---- | ---- | ----------- | ----- |
| **id** | **Integer** |  | [optional] |
| **firstname** | **String** |  | [optional] |
| **lastname** | **String** |  | [optional] |
| **email** | **String** |  | [optional] |
| **password** | **String** |  | [optional] |
| **profile_image_path** | **String** |  | [optional] |
| **company** | **String** |  | [optional] |
| **phone** | **String** |  | [optional] |
| **role** | **String** |  | [optional] |
| **enabled** | **Boolean** |  | [optional] |
| **authorities** | [**Array&lt;GrantedAuthority&gt;**](GrantedAuthority.md) |  | [optional] |
| **username** | **String** |  | [optional] |
| **account_non_locked** | **Boolean** |  | [optional] |
| **account_non_expired** | **Boolean** |  | [optional] |
| **credentials_non_expired** | **Boolean** |  | [optional] |

## Example

```ruby
require 'openapi_client'

instance = OpenapiClient::User.new(
  id: null,
  firstname: null,
  lastname: null,
  email: null,
  password: null,
  profile_image_path: null,
  company: null,
  phone: null,
  role: null,
  enabled: null,
  authorities: null,
  username: null,
  account_non_locked: null,
  account_non_expired: null,
  credentials_non_expired: null
)
```

