import { Timeline, Burst, Tween, easing } from "@mojs/core";

export class HeartAnimation {
  private timeline: Timeline;
  private el: HTMLElement;

  constructor(el: HTMLElement) {
    this.el = el;

    const scaleCurve = easing.path(
      "M0,100 L25,99.9999983 C26.2328835,75.0708847 19.7847843,0 100,0",
    );

    this.timeline = new Timeline();

    const burst1 = new Burst({
      parent: this.el,
      radius: { 0: 100 },
      angle: { 0: 45 },
      y: -10,
      count: 10,
      children: {
        shape: "circle",
        radius: 20,
        fill: ["red", "white"],
        strokeWidth: 10,
        duration: 500,
      },
    });

    const scale = new Tween({
      duration: 500,
      onUpdate: (progress: number) => {
        const scaleProgress = scaleCurve(progress);
        this.el.style.transform = `scale3d(${scaleProgress}, ${scaleProgress}, 1)`;
      },
    });

    this.timeline.add(burst1, scale);
  }

  play() {
    this.timeline.play();
  }
}
