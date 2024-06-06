// @ts-ignore
import React from "react";
import {Handle, NodeProps, Position} from 'reactflow';

type Props = {
  url: string
}

export default ({data}: NodeProps<Props>) => {

  return (
      <div>
        <Handle type="target" position={Position.Left}/>
        <div>{data.url}</div>
        <Handle type="source" position={Position.Right} id="seccess"/>
        <Handle type="source" position={Position.Right} id="fail"/>
      </div>
  );

}
