/**
 * OpenApi specification 
 * OpenApi documentation for Admin Module Demo
 *
 * The version of the OpenAPI document: 1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


export interface TableDataRequest { 
    columns?: Array<string>;
    search?: string;
    sortByColumn?: string;
    sortOrder?: string;
    limit?: number;
    offset?: number;
}

