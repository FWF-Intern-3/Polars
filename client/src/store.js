import {addBub}from './chatBubble.js'
import {userRecord}from './chatUserList.js'

/**
 * @description 本地储存用户聊天信息构造函数
 * @param {string} myname 
 * @param {string} friendName 
 * @param {string} myId 
 * @param {string} friendId 
 */
 function StoreUsersMsg(myname,friendName,myId,friendId){
    this.myname = myname,
    this.myId = myId,
    this.friendName = friendName,
    this.friendId = friendId,
    this.allmsg = []
}

/**
 * @description 储存私聊 群聊聊天信息
 * @param {*} mainData 
 * @param {*} code 
 */
export const storeFun = (mainData,code) => {   
    let userKey;  
        let obj; 
        if(code === '2'){
            userKey = mainData.fromName+mainData.toName;
            obj = new StoreUsersMsg(mainData.fromName,mainData.toName);
            let getOldVal = localStorage.getItem(userKey);

            if(getOldVal){  //  如果存在数据 则向旧数据中更新
                let tempVal = JSON.parse(getOldVal);
                let len = tempVal.allmsg.length;
                obj = tempVal;

                if(len === 50){ // 如果消息大于50条 删除！
                    obj.allmsg.splice(0,1) // 删除第一条信息
                }

                obj.allmsg[len] = mainData.data; // 存入一条聊天信息
                localStorage.setItem(`${mainData.fromName}${mainData.toName}`,JSON.stringify(obj))
            }else{  //  不存在相关数据  需要新创建并存入
                obj.allmsg[0] = mainData.data; 
                localStorage.setItem(`${mainData.fromName}${mainData.toName}`,JSON.stringify(obj))
            }

        }
        else if(code === '3'){
            userKey = mainData.toName+mainData.fromName;
            obj = new StoreUsersMsg(mainData.toName,mainData.fromName);
            let getOldVal = localStorage.getItem(userKey);

            if(getOldVal){
                let tempVal = JSON.parse(getOldVal);
                let len = tempVal.allmsg.length;
                obj = tempVal;

                if(len === 50){ // 如果消息大于50条 删除！
                    obj.allmsg.splice(0,1) // 删除第一条信息
                }

                obj.allmsg[len] = mainData.data; // 存入一条聊天信息
                localStorage.setItem(`${mainData.toName}${mainData.fromName}`,JSON.stringify(obj))
            }else{
                obj.allmsg[0] = mainData.data; 
                localStorage.setItem(`${mainData.toName}${mainData.fromName}`,JSON.stringify(obj))
            }
        }
        else if(code === '1'){
            userKey = 'group';
            obj = new StoreUsersMsg(mainData.fromName);
            let getOldVal = localStorage.getItem(userKey);

            if(getOldVal){
                let tempVal = JSON.parse(getOldVal);
                obj = tempVal;

                let msgLen = tempVal.allmsg.length;
                let tempName = mainData.fromName;
                let tempOBJ = new Object();

                tempOBJ[tempName] = mainData.data;
                obj.allmsg[msgLen] = tempOBJ;
                localStorage.setItem('group',JSON.stringify(obj))
            }else{
                let tempName = mainData.fromName;
                let tempOBJ = new Object();
                
                tempOBJ[tempName] = mainData.data;
                obj.allmsg[0] = tempOBJ;
                localStorage.setItem('group',JSON.stringify(obj))
            }
        }
}

