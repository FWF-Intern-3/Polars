import {userRecord}from './chatUserList.js'



export const reminder = (recept) => {
    let temp = recept.fromName;
    // let classLen = document.getElementById(`${userRecord.name}`).classList.length;
    // &&classLen === 3
    if(recept.code === '1'&&userRecord.name != 'group'){
        $('#group').addClass('reminder')
    }
    else if(temp != userRecord.name&&recept.code != '1'){
        $(`#${recept.fromName}`).addClass('reminder')
    }else if(temp === userRecord.name&&!judgeBox()&&recept.code != '1'){
        $(`#${recept.fromName}`).addClass('reminder')
    }
}


// 写一个方法判断 对话框是否打开  判断是否有 show class！！

/**
 * @description 判断对话框是否打开
 * @returns boolean 
 */
export const judgeBox = () => {
    let temp = document.getElementById('rightSideId').className;
    return temp === 'rightSide__show'?true:false
}

export const judgeClassLen = (val) => {
    return document.getElementById(val).classList.length;
}