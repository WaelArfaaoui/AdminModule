=begin
#OpenApi specification - Arfaoui

#OpenApi documentation for Admin Module Demo

The version of the OpenAPI document: 1.0
Contact: wael.arfaoui@talan.com
Generated by: https://openapi-generator.tech
Generator version: 7.4.0

=end

require 'cgi'

module OpenapiClient
  class UserControllerApi
    attr_accessor :api_client

    def initialize(api_client = ApiClient.default)
      @api_client = api_client
    end
    # @param user_dto [UserDto] 
    # @param [Hash] opts the optional parameters
    # @return [UserDto]
    def add(user_dto, opts = {})
      data, _status_code, _headers = add_with_http_info(user_dto, opts)
      data
    end

    # @param user_dto [UserDto] 
    # @param [Hash] opts the optional parameters
    # @return [Array<(UserDto, Integer, Hash)>] UserDto data, response status code and response headers
    def add_with_http_info(user_dto, opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: UserControllerApi.add ...'
      end
      # verify the required parameter 'user_dto' is set
      if @api_client.config.client_side_validation && user_dto.nil?
        fail ArgumentError, "Missing the required parameter 'user_dto' when calling UserControllerApi.add"
      end
      # resource path
      local_var_path = '/api/users/add'

      # query parameters
      query_params = opts[:query_params] || {}

      # header parameters
      header_params = opts[:header_params] || {}
      # HTTP header 'Accept' (if needed)
      header_params['Accept'] = @api_client.select_header_accept(['*/*'])
      # HTTP header 'Content-Type'
      content_type = @api_client.select_header_content_type(['application/json'])
      if !content_type.nil?
          header_params['Content-Type'] = content_type
      end

      # form parameters
      form_params = opts[:form_params] || {}

      # http body (model)
      post_body = opts[:debug_body] || @api_client.object_to_http_body(user_dto)

      # return_type
      return_type = opts[:debug_return_type] || 'UserDto'

      # auth_names
      auth_names = opts[:debug_auth_names] || ['bearerAuth']

      new_options = opts.merge(
        :operation => :"UserControllerApi.add",
        :header_params => header_params,
        :query_params => query_params,
        :form_params => form_params,
        :body => post_body,
        :auth_names => auth_names,
        :return_type => return_type
      )

      data, status_code, headers = @api_client.call_api(:POST, local_var_path, new_options)
      if @api_client.config.debugging
        @api_client.config.logger.debug "API called: UserControllerApi#add\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end

    # @param change_password [ChangePassword] 
    # @param [Hash] opts the optional parameters
    # @return [ChangePassword]
    def change_password(change_password, opts = {})
      data, _status_code, _headers = change_password_with_http_info(change_password, opts)
      data
    end

    # @param change_password [ChangePassword] 
    # @param [Hash] opts the optional parameters
    # @return [Array<(ChangePassword, Integer, Hash)>] ChangePassword data, response status code and response headers
    def change_password_with_http_info(change_password, opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: UserControllerApi.change_password ...'
      end
      # verify the required parameter 'change_password' is set
      if @api_client.config.client_side_validation && change_password.nil?
        fail ArgumentError, "Missing the required parameter 'change_password' when calling UserControllerApi.change_password"
      end
      # resource path
      local_var_path = '/api/users/changepassword'

      # query parameters
      query_params = opts[:query_params] || {}

      # header parameters
      header_params = opts[:header_params] || {}
      # HTTP header 'Accept' (if needed)
      header_params['Accept'] = @api_client.select_header_accept(['*/*'])
      # HTTP header 'Content-Type'
      content_type = @api_client.select_header_content_type(['application/json'])
      if !content_type.nil?
          header_params['Content-Type'] = content_type
      end

      # form parameters
      form_params = opts[:form_params] || {}

      # http body (model)
      post_body = opts[:debug_body] || @api_client.object_to_http_body(change_password)

      # return_type
      return_type = opts[:debug_return_type] || 'ChangePassword'

      # auth_names
      auth_names = opts[:debug_auth_names] || ['bearerAuth']

      new_options = opts.merge(
        :operation => :"UserControllerApi.change_password",
        :header_params => header_params,
        :query_params => query_params,
        :form_params => form_params,
        :body => post_body,
        :auth_names => auth_names,
        :return_type => return_type
      )

      data, status_code, headers = @api_client.call_api(:PATCH, local_var_path, new_options)
      if @api_client.config.debugging
        @api_client.config.logger.debug "API called: UserControllerApi#change_password\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end

    # @param id [Integer] 
    # @param [Hash] opts the optional parameters
    # @return [nil]
    def delete(id, opts = {})
      delete_with_http_info(id, opts)
      nil
    end

    # @param id [Integer] 
    # @param [Hash] opts the optional parameters
    # @return [Array<(nil, Integer, Hash)>] nil, response status code and response headers
    def delete_with_http_info(id, opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: UserControllerApi.delete ...'
      end
      # verify the required parameter 'id' is set
      if @api_client.config.client_side_validation && id.nil?
        fail ArgumentError, "Missing the required parameter 'id' when calling UserControllerApi.delete"
      end
      # resource path
      local_var_path = '/api/users/{id}'.sub('{' + 'id' + '}', CGI.escape(id.to_s))

      # query parameters
      query_params = opts[:query_params] || {}

      # header parameters
      header_params = opts[:header_params] || {}

      # form parameters
      form_params = opts[:form_params] || {}

      # http body (model)
      post_body = opts[:debug_body]

      # return_type
      return_type = opts[:debug_return_type]

      # auth_names
      auth_names = opts[:debug_auth_names] || ['bearerAuth']

      new_options = opts.merge(
        :operation => :"UserControllerApi.delete",
        :header_params => header_params,
        :query_params => query_params,
        :form_params => form_params,
        :body => post_body,
        :auth_names => auth_names,
        :return_type => return_type
      )

      data, status_code, headers = @api_client.call_api(:DELETE, local_var_path, new_options)
      if @api_client.config.debugging
        @api_client.config.logger.debug "API called: UserControllerApi#delete\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end

    # @param email [String] 
    # @param [Hash] opts the optional parameters
    # @return [User]
    def get_user(email, opts = {})
      data, _status_code, _headers = get_user_with_http_info(email, opts)
      data
    end

    # @param email [String] 
    # @param [Hash] opts the optional parameters
    # @return [Array<(User, Integer, Hash)>] User data, response status code and response headers
    def get_user_with_http_info(email, opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: UserControllerApi.get_user ...'
      end
      # verify the required parameter 'email' is set
      if @api_client.config.client_side_validation && email.nil?
        fail ArgumentError, "Missing the required parameter 'email' when calling UserControllerApi.get_user"
      end
      # resource path
      local_var_path = '/api/users/get/{email}'.sub('{' + 'email' + '}', CGI.escape(email.to_s))

      # query parameters
      query_params = opts[:query_params] || {}

      # header parameters
      header_params = opts[:header_params] || {}
      # HTTP header 'Accept' (if needed)
      header_params['Accept'] = @api_client.select_header_accept(['*/*'])

      # form parameters
      form_params = opts[:form_params] || {}

      # http body (model)
      post_body = opts[:debug_body]

      # return_type
      return_type = opts[:debug_return_type] || 'User'

      # auth_names
      auth_names = opts[:debug_auth_names] || ['bearerAuth']

      new_options = opts.merge(
        :operation => :"UserControllerApi.get_user",
        :header_params => header_params,
        :query_params => query_params,
        :form_params => form_params,
        :body => post_body,
        :auth_names => auth_names,
        :return_type => return_type
      )

      data, status_code, headers = @api_client.call_api(:GET, local_var_path, new_options)
      if @api_client.config.debugging
        @api_client.config.logger.debug "API called: UserControllerApi#get_user\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end

    # @param [Hash] opts the optional parameters
    # @return [Array<UserDto>]
    def getusers(opts = {})
      data, _status_code, _headers = getusers_with_http_info(opts)
      data
    end

    # @param [Hash] opts the optional parameters
    # @return [Array<(Array<UserDto>, Integer, Hash)>] Array<UserDto> data, response status code and response headers
    def getusers_with_http_info(opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: UserControllerApi.getusers ...'
      end
      # resource path
      local_var_path = '/api/users'

      # query parameters
      query_params = opts[:query_params] || {}

      # header parameters
      header_params = opts[:header_params] || {}
      # HTTP header 'Accept' (if needed)
      header_params['Accept'] = @api_client.select_header_accept(['*/*'])

      # form parameters
      form_params = opts[:form_params] || {}

      # http body (model)
      post_body = opts[:debug_body]

      # return_type
      return_type = opts[:debug_return_type] || 'Array<UserDto>'

      # auth_names
      auth_names = opts[:debug_auth_names] || ['bearerAuth']

      new_options = opts.merge(
        :operation => :"UserControllerApi.getusers",
        :header_params => header_params,
        :query_params => query_params,
        :form_params => form_params,
        :body => post_body,
        :auth_names => auth_names,
        :return_type => return_type
      )

      data, status_code, headers = @api_client.call_api(:GET, local_var_path, new_options)
      if @api_client.config.debugging
        @api_client.config.logger.debug "API called: UserControllerApi#getusers\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end

    # @param id [Integer] 
    # @param [Hash] opts the optional parameters
    # @option opts [String] :firstname 
    # @option opts [String] :lastname 
    # @option opts [String] :email 
    # @option opts [String] :password 
    # @option opts [String] :phone 
    # @option opts [String] :role 
    # @option opts [UpdateRequest] :update_request 
    # @return [UserDto]
    def update(id, opts = {})
      data, _status_code, _headers = update_with_http_info(id, opts)
      data
    end

    # @param id [Integer] 
    # @param [Hash] opts the optional parameters
    # @option opts [String] :firstname 
    # @option opts [String] :lastname 
    # @option opts [String] :email 
    # @option opts [String] :password 
    # @option opts [String] :phone 
    # @option opts [String] :role 
    # @option opts [UpdateRequest] :update_request 
    # @return [Array<(UserDto, Integer, Hash)>] UserDto data, response status code and response headers
    def update_with_http_info(id, opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: UserControllerApi.update ...'
      end
      # verify the required parameter 'id' is set
      if @api_client.config.client_side_validation && id.nil?
        fail ArgumentError, "Missing the required parameter 'id' when calling UserControllerApi.update"
      end
      allowable_values = ["BUSINESSEXPERT", "ADMIN", "CONSULTANT"]
      if @api_client.config.client_side_validation && opts[:'role'] && !allowable_values.include?(opts[:'role'])
        fail ArgumentError, "invalid value for \"role\", must be one of #{allowable_values}"
      end
      # resource path
      local_var_path = '/api/users/{id}'.sub('{' + 'id' + '}', CGI.escape(id.to_s))

      # query parameters
      query_params = opts[:query_params] || {}
      query_params[:'firstname'] = opts[:'firstname'] if !opts[:'firstname'].nil?
      query_params[:'lastname'] = opts[:'lastname'] if !opts[:'lastname'].nil?
      query_params[:'email'] = opts[:'email'] if !opts[:'email'].nil?
      query_params[:'password'] = opts[:'password'] if !opts[:'password'].nil?
      query_params[:'phone'] = opts[:'phone'] if !opts[:'phone'].nil?
      query_params[:'role'] = opts[:'role'] if !opts[:'role'].nil?

      # header parameters
      header_params = opts[:header_params] || {}
      # HTTP header 'Accept' (if needed)
      header_params['Accept'] = @api_client.select_header_accept(['*/*'])
      # HTTP header 'Content-Type'
      content_type = @api_client.select_header_content_type(['application/json'])
      if !content_type.nil?
          header_params['Content-Type'] = content_type
      end

      # form parameters
      form_params = opts[:form_params] || {}

      # http body (model)
      post_body = opts[:debug_body] || @api_client.object_to_http_body(opts[:'update_request'])

      # return_type
      return_type = opts[:debug_return_type] || 'UserDto'

      # auth_names
      auth_names = opts[:debug_auth_names] || ['bearerAuth']

      new_options = opts.merge(
        :operation => :"UserControllerApi.update",
        :header_params => header_params,
        :query_params => query_params,
        :form_params => form_params,
        :body => post_body,
        :auth_names => auth_names,
        :return_type => return_type
      )

      data, status_code, headers = @api_client.call_api(:PUT, local_var_path, new_options)
      if @api_client.config.debugging
        @api_client.config.logger.debug "API called: UserControllerApi#update\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end
  end
end
