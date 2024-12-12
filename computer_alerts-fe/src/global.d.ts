declare module '@mojs/core' {
    export class Timeline {
      add(...animations: any[]): void;
      play(): void;
      pause(): void;
      replay(): void;
    }
  
    export class Burst {
      constructor(options: BurstOptions);
    }
  
    export class Tween {
      constructor(options: {
        duration: number;
        onUpdate: (progress: number) => void;
        onComplete?: () => void;
      });
    }
  
    export const easing: {
      path(path: string): (progress: number) => number;
    };
  
    export interface BurstOptions {
      parent?: HTMLElement;
      radius?: number | { [key: string]: number };
      angle?: number | { [key: string]: number };
      count?: number;
      y?: number;
      children?: ChildOptions;
    }
  
    export interface ChildOptions {
      shape?: string;
      radius?: number;
      fill?: string | string[];
      stroke?: string;
      strokeWidth?: number;
      duration?: number;
    }
  
    const mojs: {
      Timeline: typeof Timeline;
      Burst: typeof Burst;
      Tween: typeof Tween;
      easing: typeof easing;
    };
  
    export default mojs;
  }
  