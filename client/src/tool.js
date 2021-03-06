/**
 * @description 通过调用该函数简写DOM查找代码
 * @param {string} id 
 * @returns object
 */
const $ = (id) => {
        return document.getElementById(id)
}

export const get = (passPlass) => {
        return document.getElementsByClassName(passPlass)[0]
}


export const getNum = () => {
        return Math.ceil(Math.random()*15)
}

export const getClasses = (cla) => {
        return document.getElementsByClassName(cla)
}