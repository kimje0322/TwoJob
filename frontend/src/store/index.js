import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
import { datastore } from './module.js'

Vue.use(Vuex);

const dataState = createPersistedState({
    paths: ['data']
})

export default new Vuex.Store({
    modules: {
        datastore
    },
    plugins: [dataState, createPersistedState()],

    state: {
        isSigned: false, // 로그인 여부
        userInfo: {
            // email: null,
            id: null,
            name: null, // 사용자 아이디 저장
            img: null,
            walletAddress: null
        },

        // 지갑이 있는지 여부
        iswallet: false,

        accessToken: null,
        address: null,
        balance: "",
        charge: null,

        askusername: null,
        askuserid: null,

    },
    mutations: {
        setAsk(state, askusername, askuserid) {
            state.askusername = askusername;
            state.askuserid = askuserid;
        },
        setCharge(state, charge) {
            state.charge = charge;
        },
        setBalance(state, balance) {
            state.balance = balance;
        },
        setAddress(state, address) {
            state.address = address;
        },
        setAccessToken(state, token) {
            state.accessToken = token;
        },
        setIsSigned(state, isSigned) {
            state.isSigned = isSigned;
        },
        setUserId(state, id) {
            state.user.id = id;
        },
        setWalletAddress(state, address) {
            state.user.walletAddress = address;
        },
        logout(state) {
            state.isSigned = false;
            state.user.id = 0;
            state.user.walletAddress = null;
        },
        setUserInfo(state, userinfo) {
            console.log(userinfo)
                // state.userInfo.email = userinfo.email;
            state.userInfo.id = userinfo.id;
            state.userInfo.name = userinfo.name;
            state.userInfo.img = userinfo.img;
            state.isSigned = userinfo.login;
        },
        deluserInfo(state) {
            state.userInfo.id = null;
            state.userInfo.name = null;
            state.userInfo.img = null;
            state.isSigned = false;
        },
    },
    actions: {},
    modules: {}
});