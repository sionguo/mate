import {NodeProps} from "reactflow";
import {useState} from "react";
import styles from "./Demo.module.css"

export type CounterData = {
  initialCount?: number;
};

export default function Demo(props: NodeProps<CounterData>) {
  const [count, setCount] = useState(props.data?.initialCount ?? 0);

  return (
      <div className={styles.wrapper}>
        <p>Count: {count}</p>
        <button className="nodrag" onClick={() => setCount(count + 1)}>
          Increment
        </button>
      </div>
  );
}
