import {getNum} from './tool.js';
/**
 * @description 添加聊天气泡
 * @param {string} msg 
 * @param {string} myname 
 * @param {string} imgUrl 
 * @param {Boolean} bool  true: 我  false: 他人
 */
export const addBub = (msg,myname,imgUrl,bool) => {
    if(myname != null){
        addBubGroup(msg,myname,imgUrl,bool)  // 
        return null;
    }
    let num = $('.bubble').length;
    if(bool) {
        let html = '<li class="rightMsg bubble">'
        +'<div>'
        +'<div class="msgBubble">来啦来啦！！！！！</div>'
        +'</div>'
        +'<img src="/static/img/1616388899992.jpg" alt="" class="bubbleImg">'
        +'</li>';
        let bubs = document.getElementsByClassName('msgBar')[0];
        bubs.innerHTML += html;

        let newBub = $('.msgBubble');
        newBub[num].innerHTML = msg;

        // 更改头像
        // let img = $('.bubbleImg');
        // img[num].src = imgUrl;


        // 保持到最低端？？？
        // let tempEle = document.getElementsByClassName('bubble')[0];
        // tempEle.scrollIntoView()
        // let tempEle = $('li.bubble:last');
        // tempEle.scrollIntoView({block: "end"})
    }else{
        let html = '<li class="leftMsg bubble">'
        +'<img src="/static/img/1616388899992.jpg" alt="" class="bubbleImg">'
        +'<div>'
        +'<div class="msgBubble">上号上号！</div>'
        +'</div>'
        +'</li>';
        let bubs = document.getElementsByClassName('msgBar')[0];
        bubs.innerHTML += html;

        let newBub = $('.msgBubble');
        newBub[num].innerHTML = msg;

        // let img = $('.bubbleImg');
        // img[num].src = `/static/img/Avatar${getNum()}.png`;
        // let tempEle = document.getElementsByClassName('bubble')[0];
        // tempEle.scrollIntoView()
        // let tempEle = $('li.bubble:last');
        // tempEle.scrollIntoView({block: "end"})
    }
}

export const addBubGroup = (msg,myname,imgUrl,bool) => {
    let num = $('.groupbubble').length;
    if(bool){
        let html = '<li class="rightMsgG groupbubble">'
        +'<div style="display:grid;grid-template-rows:1fr 2fr;">'
        +'<div class="group-users-name" style="color: white;font-size: 10px;justify-self: right;">jinxi</div>'
        +'<div class="msgBubbleG">冲冲冲</div>'
        +'</div>'
        +'<img src="/static/img/1616388899992.jpg" alt="" class="bubbleImgG">'
        +'</li>';
        let bubs = document.getElementsByClassName('msgBar')[0];
        bubs.innerHTML += html;

        msg = msg.replace(msg.charAt(0),'');  // 去 $ 

        let newBub = $('.msgBubbleG');
        newBub[num].innerHTML = msg;

        let newbubName = $('.group-users-name');
        newbubName[num].innerHTML = myname;
    }else{
        let html = '<li class="leftMsgG groupbubble">'
        +'<img src="/static/img/1616388899992.jpg" alt="" class="bubbleImgG">'
        +'<div style="display:grid;grid-template-rows:1fr 2fr;">'
        +'<div class="group-users-name" style="color: white;font-size: 10px;justify-self: left;">jinxi</div>'
        +'<div class="msgBubbleG">冲冲冲</div>'
        +'</div>'
        +'</li>'
        let bubs = document.getElementsByClassName('msgBar')[0];
        bubs.innerHTML += html;

        let newBub = $('.msgBubbleG');
        newBub[num].innerHTML = msg;

        let img = $('.bubbleImgG');
        img[num].src = `/static/img/Avatar${getNum()}.png`;

        let newbubName = $('.group-users-name');
        newbubName[num].innerHTML = myname;
    }
}
