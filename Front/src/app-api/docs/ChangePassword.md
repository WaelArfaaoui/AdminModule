# OpenapiClient::ChangePassword

## Properties

| Name | Type | Description | Notes |
| ---- | ---- | ----------- | ----- |
| **current_password** | **String** |  | [optional] |
| **new_password** | **String** |  | [optional] |
| **confirmation_password** | **String** |  | [optional] |
| **message** | **String** |  | [optional] |

## Example

```ruby
require 'openapi_client'

instance = OpenapiClient::ChangePassword.new(
  current_password: null,
  new_password: null,
  confirmation_password: null,
  message: null
)
```

