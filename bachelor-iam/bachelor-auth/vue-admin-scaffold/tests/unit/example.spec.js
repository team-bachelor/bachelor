import { shallowMount } from '@vue/test-utils';
import Echart from '@/components/Echart.vue';

describe('Echart.vue', () => {
  it('renders props.msg when passed', () => {
    const msg = 'new message';
    const wrapper = shallowMount(Echart, {
      propsData: { msg },
    });
    expect(wrapper.text()).toMatch(msg);
  });
});
