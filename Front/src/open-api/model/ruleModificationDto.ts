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
import { RuleDto } from './ruleDto';


export interface RuleModificationDto { 
    id?: number;
    ruleDto?: RuleDto;
    modificationDate?: string;
    modifiedBy?: string;
    ruleName?: string;
    ruleId?: number;
    modificationDescription?: string;
    modificationType?: string;
    profileImagePath?: string;
}

