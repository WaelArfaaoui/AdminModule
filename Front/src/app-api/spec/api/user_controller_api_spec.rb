=begin
#OpenApi specification - Arfaoui

#OpenApi documentation for Admin Module Demo

The version of the OpenAPI document: 1.0
Contact: wael.arfaoui@talan.com
Generated by: https://openapi-generator.tech
Generator version: 7.4.0

=end

require 'spec_helper'
require 'json'

# Unit tests for OpenapiClient::UserControllerApi
# Automatically generated by openapi-generator (https://openapi-generator.tech)
# Please update as you see appropriate
describe 'UserControllerApi' do
  before do
    # run before each test
    @api_instance = OpenapiClient::UserControllerApi.new
  end

  after do
    # run after each test
  end

  describe 'test an instance of UserControllerApi' do
    it 'should create an instance of UserControllerApi' do
      expect(@api_instance).to be_instance_of(OpenapiClient::UserControllerApi)
    end
  end

  # unit tests for add
  # @param user_dto 
  # @param [Hash] opts the optional parameters
  # @return [UserDto]
  describe 'add test' do
    it 'should work' do
      # assertion here. ref: https://rspec.info/features/3-12/rspec-expectations/built-in-matchers/
    end
  end

  # unit tests for change_password
  # @param change_password 
  # @param [Hash] opts the optional parameters
  # @return [ChangePassword]
  describe 'change_password test' do
    it 'should work' do
      # assertion here. ref: https://rspec.info/features/3-12/rspec-expectations/built-in-matchers/
    end
  end

  # unit tests for delete
  # @param id 
  # @param [Hash] opts the optional parameters
  # @return [nil]
  describe 'delete test' do
    it 'should work' do
      # assertion here. ref: https://rspec.info/features/3-12/rspec-expectations/built-in-matchers/
    end
  end

  # unit tests for get_user
  # @param email 
  # @param [Hash] opts the optional parameters
  # @return [User]
  describe 'get_user test' do
    it 'should work' do
      # assertion here. ref: https://rspec.info/features/3-12/rspec-expectations/built-in-matchers/
    end
  end

  # unit tests for getusers
  # @param [Hash] opts the optional parameters
  # @return [Array<UserDto>]
  describe 'getusers test' do
    it 'should work' do
      # assertion here. ref: https://rspec.info/features/3-12/rspec-expectations/built-in-matchers/
    end
  end

  # unit tests for update
  # @param id 
  # @param [Hash] opts the optional parameters
  # @option opts [String] :firstname 
  # @option opts [String] :lastname 
  # @option opts [String] :email 
  # @option opts [String] :password 
  # @option opts [String] :phone 
  # @option opts [String] :role 
  # @option opts [UpdateRequest] :update_request 
  # @return [UserDto]
  describe 'update test' do
    it 'should work' do
      # assertion here. ref: https://rspec.info/features/3-12/rspec-expectations/built-in-matchers/
    end
  end

end
