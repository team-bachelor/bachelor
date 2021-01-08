<template>
<div class='page'>
    <div class='_404'>404</div>
    <hr>
    <div class='_1'>THE PAGE</div>
    <div class='_2'>WAS NOT FOUND</div>
    <a class='btn' href="javascript:;" @click="$router.push('/')">BACK TO HOME</a>
</div>
</template>
<script>
export default {
  name: 'NOTFIND404',
  data() {
    return {
    };
  },
};
</script>
<style scoped lang="scss">
@import "../../assets/styles/variable.scss";
.page{
  text-align: center;
  display: block;
  position: relative;
  width:80%;
  margin:100px auto;
}
._404{
  font-size: 220px;
  position: relative;
  display: inline-block;
  z-index: 2;
  height: 250px;
  letter-spacing: 15px;
}
._1{
  text-align:center;
  display:block;
  position:relative;
  letter-spacing: 12px;
  font-size: 4em;
  line-height: 80%;
}
._2{
  text-align:center;
  display:block;
  position: relative;
  font-size: 20px;
}

.btn{
  background-color: rgb( 255, 255, 255 );
  position: relative;
  display: inline-block;
  width: 358px;
  padding: 5px;
  z-index: 5;
  font-size: 25px;
  margin:0 auto;
  color: $--color-primary;
  text-decoration: none;
  margin-right: 10px
}

hr{
  padding: 0;
  border: none;
  border-top: 10px solid #333;
  opacity: 0.8;
  text-align: center;
  margin: 0px auto;
  width: 420px;
  height:10px;
  z-index: -10;
}

</style>
