import React from 'react'
import { Rating } from './Rating'

export const Comment = (props) => {
    return (
        <div key={props.index} className="card mb-3">
            <div className="card-body">
                <div className="d-flex flex-start">
                    <img className="rounded-circle shadow-1-strong me-3"
                        src="https://inkythuatso.com/uploads/thumbnails/800/2022/05/63d4c026c181357e6c08ada62db59b8b-12-08-49-45.jpg" alt="avatar" width="40"
                        height="40" />
                    <div className="w-100">
                        <div className="d-flex justify-content-between align-items-center mb-3">
                            <h6 className="text-primary fw-bold mb-0">
                                {props.fb.username}
                                <span className="text-dark ms-2">{props.fb.message}</span>
                            </h6>
                            <p className="mb-0">{props.fb.time}</p>
                        </div>
                        <div className="d-flex justify-content-between align-items-center">
                            <p className="small mb-0" style={{ color: "#aaa" }}>
                            </p>
                            <div className="flex-row">
                                <Rating rating={props.fb.rate} readOnly={true} />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
