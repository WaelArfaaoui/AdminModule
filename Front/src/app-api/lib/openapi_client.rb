=begin
#OpenApi specification - Arfaoui

#OpenApi documentation for Admin Module Demo

The version of the OpenAPI document: 1.0
Contact: wael.arfaoui@talan.com
Generated by: https://openapi-generator.tech
Generator version: 7.4.0

=end

# Common files
require 'openapi_client/api_client'
require 'openapi_client/api_error'
require 'openapi_client/version'
require 'openapi_client/configuration'

# Models
require 'openapi_client/models/authentication_request'
require 'openapi_client/models/authentication_response'
require 'openapi_client/models/change_password'
require 'openapi_client/models/granted_authority'
require 'openapi_client/models/update_request'
require 'openapi_client/models/user'
require 'openapi_client/models/user_dto'

# APIs
require 'openapi_client/api/authentication_controller_api'
require 'openapi_client/api/user_controller_api'

module OpenapiClient
  class << self
    # Customize default settings for the SDK using block.
    #   OpenapiClient.configure do |config|
    #     config.username = "xxx"
    #     config.password = "xxx"
    #   end
    # If no block given, return the default Configuration object.
    def configure
      if block_given?
        yield(Configuration.default)
      else
        Configuration.default
      end
    end
  end
end
