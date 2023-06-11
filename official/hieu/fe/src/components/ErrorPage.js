import React from 'react'

export const ErrorPage = (props) => {

    return (
        <div>
            <div> Không thể truy cập!</div>
            <h2><a href={props.position === 'user' ? '/home' : '/admin'}>Quay lại trang chủ</a></h2>
        </div>
    )
}
