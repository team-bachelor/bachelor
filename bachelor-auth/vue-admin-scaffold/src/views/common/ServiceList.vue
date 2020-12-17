<template>
    <div v-loading="loading" class="container">
        <transition-stagger
                v-if="services.length && !loading"
                :before-enter-styles="{translateY: -20, opacity: 0}"
                :enter-styles="{translateY: 0, opacity: 1}"
                :leave-styles="{}"
                :delay="80"
                tag="div"
                class="service-list">
            <a class="service-item" v-for="(item,index) in services"
               :href="item.url"
               :key="index">
                <h4>{{item.name}}</h4>
                <i></i>
            </a>
        </transition-stagger>
        <div v-if="!services.length && !loading" class="no-data">无数据</div>
    </div>
</template>
<script>
import TransitionStagger from '@/components/Stagger.vue';

export default {
    name: 'ServiceList',
    components: {
        TransitionStagger,
    },
    data() {
        return {
            services: [],
            loading: true,
        };
    },
    async created()
{
    await
    this.fetch();
}
,
beforeDestroy()
{
    this.services = [];
}
,
methods: {
    async fetch()
    {
        this.loading = true;
        try {
            const {data} = await
            this.$api.authorizedApp({ userId: this.$store.state.userinfo.userId });
            if (data.status !== 'OK') {
                throw new Error(data.msg);
            }
            this.loading = false;
            this.$nextTick(() => {
                this.services = data.data;
        })
            ;
        } catch (e) {
            this.loading = false;
            this.$message.error(e.msg || e.message || e);
        }
    }
,
}
,
}
;
</script>
<style lang="scss" scoped>
.container {
    height: 100%;
}

.service-list {
    display: flex;
    justify-content: center;
    align-items: center;
    align-content: center;
    flex-wrap: wrap;
    overflow: auto;
    height: 100%;
}

.service-item {
    width: 230px;
    height: 60px;
    margin: 10px;
    color: #666;
    border-radius: 3px;
    box-shadow: 0 2px 0 rgba(48, 65, 86, 0.1), 0 0 3px rgba(56, 56, 56, 0.25);
    cursor: pointer;
    background: #FFF;
    transition: box-shadow 0.218s ease;
    position: relative;

&
:hover {
    box-shadow: 0 5px 10px rgba(48, 65, 86, 0.25);

h4 {
    transform: translateY(-3%);
}

i {
    transition: all 0.4s;
    transform: rotate(180deg);
}

}
i {
    position: absolute;
    bottom: 5px;
    right: 5px;
    background: url(../../assets/images/authorizedApp.png) no-repeat;
    background-size: contain;
    width: 16px;
    height: 16px;
    transition: all 0.3s;
}

h4 {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    top: 0;
    line-height: 60px;
    margin: 0;
    padding: 0;
    text-align: center;
    transform: translateY(0%);
    transition: transform 0.4s;
}

}
.no-data {
    padding: 50px 0;
    color: #888;
    text-align: center;
}
</style>
