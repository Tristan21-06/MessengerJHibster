import { type IMessage } from '@/shared/model/message.model';

export interface IReaction {
  id?: number;
  type?: string | null;
  message?: IMessage | null;
}

export class Reaction implements IReaction {
  constructor(
    public id?: number,
    public type?: string | null,
    public message?: IMessage | null,
  ) {}
}
