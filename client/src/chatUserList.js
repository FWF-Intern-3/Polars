import {addBub}from './chatBubble.js'
import {reminder,judgeBox,judgeClassLen}from './msgReminder.js'
/**目前用户名只支持英文 */
/**
 * @description 添加新用户  并设置唯一与用户名相同的id
 * @param {string} user 
 * @param {string} userName   (jinxi,jinxiName) 
 * @param              ('jinxi','jinxiName','眼看你起朱楼，眼看你宴宾客，眼看你楼塌了','http:...') 
 */
export const addUser = (user,userName,signature,imgUrl) => { 
        let goal = document.getElementsByClassName('usersListBox')[0];
        let num = ($('.user')).length;
        let num2 = ($('.userNameInsideL')).length;

        let html = '<li  class="userOne user li" id="">'
        +`<img src="/static/img/Avatar1.png" alt="头像" class="headImg" width="55px" height="50px">`
        +'<div class="usersNameInfor">'
        +'<div class="userName">'
        +'<div class="userNameInsideL" id="">向兴强</div>'
        +'</div>'
        +'<div class="signature">Hello World!</div>'
        +'</div>'
        +'</li>';
    
        goal.innerHTML += html;
    
    
        let toSetOne = $('.user')[num];
        let toSetTwo = $('.userNameInsideL')[num2];
    
        // 设置唯一与用户名相同的id
        toSetOne.id = user;  
        toSetTwo.id = userName;
        toSetTwo.innerHTML = user;
    
        if(imgUrl){
            let num3 = $('.headImg').length;
            let setImg = $('.headImg')[num3-1];
            setImg.src = imgUrl;
        }
    
        if(signature){
            let num4 = $('.signature').length;
            let setSignature = $('.signature')[num4-1];
            setSignature.innerHTML = signature;
        }
        
        // 初始化 设置点击事件 以打开关闭对应的聊天框
        init()
}
    
    
    /**
     * @description 成功打开关闭盒子的关键————改变class 以改变样式
     */
    export const openBox = () => {
        $('#chatBoxId').toggleClass('chatBox');
        $('#chatBoxId').toggleClass('chanChatBox');

        $('#leftSideId').toggleClass('leftSide');
        $('#leftSideId').toggleClass('chanLeftSide');
        
        $('#rightSideId').toggleClass('rightSide__hide');
        $('#rightSideId').toggleClass('rightSide__show');
    }
    
    

/**
 * @description 当点击用户时 对话框显示所储存的本地消息
 * @param {string} elementId  
 * @param {Boolean} bool 
 */
    export const addEvent = (elementId,bool) => {
        $(`#${elementId}`).click(() => {
            openBox();
            if(judgeBox()){
                userRecord.name = elementId;
            }else{
                userRecord.name = 'abcd';
            }
            
            // 关闭消息提示
            if(judgeBox()){
                if(judgeClassLen(elementId) === 4){
                    $(`#${elementId}`).removeClass('reminder')
                }
            }

            $('.RUserName').html(`${$(`#${elementId}Name`).html()}`);
            let key = null;

            if(bool){ // 单个用户
                let tempUser = JSON.parse(localStorage.getItem('logInUser')).name;
                key = tempUser+userRecord.name;  // 改为群聊这里需要变
            }else{   // 群聊
                key = userRecord.name;
            }

            $('.msgBar').html('');
            // JSON.parse(localStorage.getItem(key)).friendName!=null
            if(localStorage.getItem(key)&&key != 'group'){
                let val = JSON.parse(localStorage.getItem(key));

                for(let i = 0;i<val.allmsg.length;i++){

                    if(val.allmsg[i].charAt(0) === '$'){  //添加条件 读取信息 是否以 $ 开头
                        val.allmsg[i] = val.allmsg[i].replace('$','')  //  去除 $
                        addBub(val.allmsg[i],null,null,true)  //  我发的信息  右边显示
                    }
                    else{
                        addBub(val.allmsg[i],null,null,false) //  别人发的信息  左边显示
                    }

                }

            }
            else if(localStorage.getItem(key)&&key === 'group'){
                let val = JSON.parse(localStorage.getItem(key));

                for(let i = 0;i<val.allmsg.length;i++){
                    let attrList = Object.keys(val.allmsg[i])[0];
                    let aim = val.allmsg[i][attrList];

                    if(aim.charAt(0) === '$'){ 
                        //val.allmsg[i] = val.allmsg[i].replace(val.allmsg[i].charAt(0),'')  //  去除 $
                        aim.replace('$','');
                        addBub(aim,attrList,null,true)  
                    }
                    else{
                        addBub(aim,attrList,null,false) 
                    }

                }
            }
        });
    }
    /**
     * @description 点击列表用户时 显示有对应用户名的对话框
     * @param {string} elementId 
     */
    export const userRecord = new Object();
    userRecord.name = 'abcd';
    userRecord.id = '';
    export const openChatBoxEach = (elementId) => {
        if(elementId === 'group'){
            addEvent(elementId,false) // 群聊
        }else{       
            addEvent(elementId,true) // 单聊
        }
    }
    
    
    /**
     * @description 将每个用户条初始化添加点击事件
     */
    export const init = () => {
        let len = ($('.user')).length;
        let list = $('.user');
        for(let i = 0;i<len;i++){
            openChatBoxEach(list[i].id);
        }
    }
    
    
    /**
     * @description 加载文件（网页）时自动执行最初始化功能！
     */
    export const goFirst = (() => {
        init();
        $('.iconguanbi').click(() => {openBox();userRecord.name = 'abcd';});
    })()





    