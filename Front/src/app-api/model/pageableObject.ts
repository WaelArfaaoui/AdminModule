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
import { SortObject } from './sortObject';


export interface PageableObject { 
    offset?: number;
    sort?: SortObject;
    pageSize?: number;
    pageNumber?: number;
    paged?: boolean;
    unpaged?: boolean;
}

