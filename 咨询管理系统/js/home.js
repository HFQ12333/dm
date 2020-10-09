$(function(){
    $('ul').on('click','li',function(){
        console.log($(this).text())
        var text=$(this).text()
        if(text =='栏目管理'){
            $('.content').load('./page/category.html')
        }
        else if(text=='文章管理'){
            $('.content').load('./page/article.html')
        }
        else if(text=='用户管理'){
            $('.content').load('./page/user.html')
        }
        
    })
})