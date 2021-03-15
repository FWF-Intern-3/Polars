/**
 * @description 通过调用该函数简写DOM查找代码
 * @param {string} id 
 * @returns object
 */
export const $ = function(id){
        return document.getElementById(id)
}