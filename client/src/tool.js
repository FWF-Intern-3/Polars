/**
 * @description 通过调用该函数简写DOM查找代码
 * @param {string} id 
 * @returns object
 */
export const $ = (id) => {
        return document.getElementById(id)
}

export const get = (passPlass) => {
        return document.getElementsByClassName(passPlass)[0]
}


export const getNum = () => {
        return Math.ceil(Math.random()*15)
}

